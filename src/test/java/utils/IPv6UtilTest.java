package utils;

import org.example.utils.IPv6Util;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IPv6UtilTest {

    @Test
    public void testGetLocalIPv6Address() {
        String localIPv6Address = IPv6Util.getLocalIPv6Address();
        System.out.println("localIPv6Address: " + localIPv6Address);
        // 检查返回的 IPv6 地址是否为空
        assertNotNull(localIPv6Address, "Local IPv6 address should not be null");

        // 检查返回的 IPv6 地址是否符合 IPv6 格式
        assertTrue(localIPv6Address.matches("^[0-9a-fA-F:]+$"), "Local IPv6 address should match IPv6 format");
    }
}