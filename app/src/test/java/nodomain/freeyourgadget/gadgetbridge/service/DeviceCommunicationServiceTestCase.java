package nodomain.freeyourgadget.gadgetbridge.service;

import android.content.Context;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import nodomain.freeyourgadget.gadgetbridge.GBException;
import nodomain.freeyourgadget.gadgetbridge.impl.GBDevice;
import nodomain.freeyourgadget.gadgetbridge.model.DeviceType;
import nodomain.freeyourgadget.gadgetbridge.test.TestBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeviceCommunicationServiceTestCase extends TestBase {
    private static final java.lang.String TEST_DEVICE_ADDRESS = TestDeviceSupport.class.getName();

    /**
     * Factory that always returns the mockSupport instance
     */
    private class TestDeviceSupportFactory extends DeviceSupportFactory {
        public TestDeviceSupportFactory(Context context) {
            super(context);
        }

        @Override
        public synchronized DeviceSupport createDeviceSupport(GBDevice device) throws GBException {
            return mockSupport;
        }
    }

    private TestDeviceService mDeviceService;
    @Mock
    private TestDeviceSupport realSupport;
    private TestDeviceSupport mockSupport;

    public DeviceCommunicationServiceTestCase() {
        super();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockSupport = null;
        realSupport = new TestDeviceSupport();
        realSupport.setContext(new GBDevice(TEST_DEVICE_ADDRESS, "Test Device", DeviceType.TEST), null, getContext());
        mockSupport = Mockito.spy(realSupport);
        DeviceCommunicationService.setDeviceSupportFactory(new TestDeviceSupportFactory(getContext()));

        mDeviceService = new TestDeviceService(getContext());
    }

    protected GBDevice getDevice() {
        return realSupport.getDevice();
    }

    @Override
    public void tearDown() throws Exception {
        mDeviceService.stopService(mDeviceService.createIntent());
        super.tearDown();
    }

    @Test
    public void testNotConnected() {
        GBDevice device = getDevice();
        assertEquals(GBDevice.State.NOT_CONNECTED, device.getState());

        // verify that the events like onFindDevice do not reach the DeviceSupport instance,
        // because not connected
        InOrder inOrder = Mockito.inOrder(mockSupport);
        mDeviceService.onFindDevice(true);
        inOrder.verify(mockSupport, Mockito.times(0)).onFindDevice(true);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void ensureConnected() {
        mDeviceService.start();
        // connection goes synchronously here
        mDeviceService.connect(getDevice());
        Mockito.verify(mockSupport, Mockito.times(1)).connect();
        assertTrue(getDevice().isInitialized());
    }

    @Test
    public void testFindDevice() {
        ensureConnected();

        InOrder inOrder = Mockito.inOrder(mockSupport);
        mDeviceService.onFindDevice(true);
        mDeviceService.onFindDevice(false);
        inOrder.verify(mockSupport, Mockito.times(1)).onFindDevice(true);
        inOrder.verify(mockSupport, Mockito.times(1)).onFindDevice(false);
        inOrder.verifyNoMoreInteractions();
    }

}
