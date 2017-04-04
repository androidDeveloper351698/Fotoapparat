package io.fotoapparat;

import android.content.Context;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatBuilderTest {

    @Mock
    Context context;
    @Mock
    CameraProvider cameraProvider;
    @Mock
    CameraRenderer cameraRenderer;
    @Mock
    SelectorFunction<Size> photoSizeSelector;
    @Mock
    SelectorFunction<LensPosition> lensPositionSelector;
    @Mock
    SelectorFunction<FocusMode> focusModeSelector;
    @Mock
    Logger logger;

    @Before
    public void setUp() throws Exception {
        given(context.getSystemService(Context.WINDOW_SERVICE))
                .willReturn(Mockito.mock(WindowManager.class));
    }

    @Test
    public void onlyMandatoryParameters() throws Exception {
        // Given
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // When
        Fotoapparat result = builder.build();

        // Then
        assertNotNull(result);
    }

    @Test
    public void cameraProvider_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.cameraProvider);
    }

    @Test
    public void cameraProvider_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .cameraProvider(cameraProvider);

        // Then
        assertEquals(
                cameraProvider,
                builder.cameraProvider
        );
    }

    @Test
    public void logger_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.logger);
    }

    @Test
    public void logger_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .logger(logger);

        // Then
        assertEquals(
                logger,
                builder.logger
        );
    }

    @Test
    public void focusMode_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.focusModeSelector);
    }

    @Test
    public void focusMode_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .focusMode(focusModeSelector);

        // Then
        assertEquals(
                focusModeSelector,
                builder.focusModeSelector
        );
    }

    @Test(expected = IllegalStateException.class)
    public void rendererIsMandatory() throws Exception {
        // Given
        FotoapparatBuilder builder = new FotoapparatBuilder(context)
                .photoSize(photoSizeSelector)
                .lensPosition(lensPositionSelector);

        // When
        builder.build();

        // Then
        // Expect exception
    }

    @Test(expected = IllegalStateException.class)
    public void lensPositionIsMandatory() throws Exception {
        // Given
        FotoapparatBuilder builder = new FotoapparatBuilder(context)
                .photoSize(photoSizeSelector)
                .into(cameraRenderer);

        // When
        builder.build();

        // Then
        // Expect exception
    }

    private FotoapparatBuilder builderWithMandatoryArguments() {
        return new FotoapparatBuilder(context)
                .lensPosition(lensPositionSelector)
                .into(cameraRenderer);
    }
}