package com.fpeng2288.authguard.util;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: MaskUtil
 * Package: com.fpeng2288.authguard.util
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 0:23
 * @version 1.0
 */
public class MaskUtil {

    private MaskUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Masks an email address.
     * Shows only the first character and the domain part.
     * Example: "example@domain.com" becomes "e****@domain.com"
     *
     * @param email the email address to mask
     * @return the masked email address, or null if the input is null
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email; // Can't mask effectively if there's only one character before @
        }
        String namePart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);
        return namePart.charAt(0) + StringUtils.repeat('*', namePart.length() - 1) + domainPart;
    }
}
