package com.hntxrj.txerp.service;

import com.aliyuncs.exceptions.ClientException;
import com.hntxrj.txerp.core.exception.ErpException;

public interface AuthCodeService {

    /**
     * send auth code
     *
     * @param phoneNumber user phone number
     * @param type        The verification code is of that type (when used)
     * @return return auth code row id
     */
    String sendAuthCode(String phoneNumber, Integer type) throws ErpException, ClientException;


    /**
     * right ?
     *
     * @param phoneNumber user phone number
     * @param code        auth code value
     * @param type        auth code type
     * @param id          auth code id
     * @return right return true, error return false
     */
    boolean pass(String phoneNumber, String code, Integer type, String id);
}
