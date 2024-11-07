package utils;

import org.example.utils.IPUtil;
import org.junit.jupiter.api.Test;

import static org.example.utils.IPUtil.isIPv4;
import static org.junit.jupiter.api.Assertions.*;

public class IPUtilTest {

    @Test
    public void testGetLocalIPv6Address() {
        String localIPv6Address = IPUtil.getLocalIPv6Address();
        System.out.println("localIPv6Address: " + localIPv6Address);
        // 检查返回的 IPv6 地址是否为空
        assertNotNull(localIPv6Address, "Local IPv6 address should not be null");

        // 检查返回的 IPv6 地址是否符合 IPv6 格式
        assertTrue(localIPv6Address.matches("^[0-9a-fA-F:]+$"), "Local IPv6 address should match IPv6 format");
    }

    @Test
    public void testGetLocationIPv4Address() {
        String localIPv4Address = IPUtil.getLocalIPv4Address();
        System.out.println("localIPv4Address: " + localIPv4Address);
        // 检查返回的 IPv6 地址是否为空
        assertNotNull(localIPv4Address, "Local IPv4 address should not be null");
        assertTrue(isIPv4(localIPv4Address));
    }
}