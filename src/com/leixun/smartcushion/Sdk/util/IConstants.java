package com.leixun.smartcushion.Sdk.util;

/**
 * Created by 409153 on 2015/6/16.
 */
public class IConstants {
	
	public final static String PRE_NAME = "BraceletXML";
	
//    public static final String HTTP_URL = "http://10.84.77.72:8089/Shouhuan/dataService.html";
//    public static final String APP_HTTP_URL = "http://61.174.13.182:7071/xiaoleida/app/Android/";
	public static final String CHECKUPDATE_APP_URL = "http://query.hwcloudtest.cn/AirRadar/v2/checkEx.action";
	
	/* 密钥内容 base64 code */
	public static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNII7xX1t8ClXb04+" +
			"QJrBdfSvSNx/e5pmCQXNugn+iDvAjwxYin2rHtV4kO1gaKrsqsLhc0yZudwxd5y" +
			"Mz7UGvscfdNgPXVMLUK+pDiEJnvT8FYoSMG";
	public static final String KEY_USERDATA = "userData";
    public static final String KEY_RESULT = "result";
    public static final String KEY_DATA = "data";
  
    
    
    public static final String BLE_SERVICE_ACTION = "com.tcl.sport.health.bluetooth.BluetoothLeService";

    //HTTP Request Code
    public static final String LOGIN_CODE = "1001";
    
    public static final String REGIST_CODE = "1002";
    public static final String ACK_CODE_USER_EXSITED = "1002";
    
    public static final String UPDATE_INFO = "1003";
    public static final String ACK_CODE_VALIDATION_CODE_SEND_FAILURE = "1004";
    public static final String UPDATE_PSWD_MOBILE = "1005";
    public static final String SUBMIT_INFO = "1006";
    public static final String UPDATE_AIM = "1007";
    public static final String GET_INFO = "1008";
    
    public static final String UPDATE_IDATA = "6000";
    public static final String GET_IDATA = "6001";
   
   
    public static final String VALIDATION_CODE = "1031";
    public static final String MAIL_CODE = "1032";
    public static final String UPDATE_PSWD_EMAIL = "1033";
    //HTTP Response Code
    public static final String HTTP_SUCCESS = "0";
    public static final String AUTHENTICATION_ERROR_CODE = "1001";
    
    
   
    public static final String NOUSER_ERROR_CODE = "1003";
    public static final String CODE_ERROR_CODE = "1004";
    public static final String PHONE_ERROR_CODE = "1005";
    public static final String ORGINPASSWD_ERROR_CODE = "1006";
    public static final String NOUSERSS_ERROR_CODE = "1007";
    public static final String TOKEN_ERROR_CODE = "1008";

    
    
    public static final String UPDATE_VERSION = "4002";
    //Font Type
    public static final String FONT_TYPE_FZLTHK = "FZLTH";
    public static final String FONT_TYPE_FZLTXHK = "FZLTXH";
    public static final String FONT_TYPE_GOTHAMEXLIGHT = "GOTHAMEXLIGHT";
    public static final String FONT_TYPE_GOTHAMEXLIGHTITALIC = "GOTHAMEXLIGHTITALIC";
    
    public static final String ACK_CODE_SUCCESS = "0";
    
    
    public static final int ENABLE_BT_REQ = 0;
    public static final int SELECT_FILE_REQ = 1;
    public static final int SELECT_INIT_FILE_REQ = 2;
    public static final String APP_FILE_NAME = "/App/SmallRadar";  
    public static final String DFU_FILE_NAME = "/hwble/smallradar";  
    public static final int GET_CURR_STEPS_PRIOND = 1*1000;
    public static final int SEND_CMD_PRIOND = 200;
    
    public static final int DFU_IDEL = 0;
    public static final int DFU_OPENING = 1;
    public static final int DFU_OPENED = 2;
    public static final int DFU_UPDATE_FAILED = 3;
    public static final int DFU_UPDATEING = 4;
    public static final int DFU_UPDATE_FINISHED = 5;
    
    
    public static final int STATE_DATA_SEND_IDLE = 0;
    public static final int STATE_DATA_SENDING = 1;
    public static final int STATE_DATA_SEND_RESPONSE_ACK = 2;
    public static final int STATE_DATA_SEND_RESPONSE_NACK = 3;
    public static final int STATE_DATA_SEND_TIMOUT = 4;
    
    public static final int STATE_LONG_DATA_SEND_IDLE = 5;
    public static final int STATE_LONG_DATA_SENDING = 6;
    
    
    public static final String IS_FIRST_START_APPLICATION = "isFirstStart";
    public static final String LOGIN_INFORMATION= "shouhuan_userinfo";
    
    public static String CLOCK_CYCLE_FLAG = "clock_cycle_flag";
    public static final int CLOCK_CYCLE_TYPE_ONCE = 0;
    public static final int CLOCK_CYCLE_TYPE_EVERTDAY = 1;
    public static final int CLOCK_CYCLE_TYPE_WORKDAY = 2;
    public static final int CLOCK_CYCLE_TYPE_CUSTOM = 3;
    
    public static final int SWITCH_OFF = 0;
    public static final int SWITCH_ON = 1;
    
    public static final int CLOCK_REPEAT_TYPE_OFF = 0;
    public static final int CLOCK_REPEAT_TYPE_ON = 1;
    public static final int CLOCK_INTELIGENT_WEAKUP_TYPE_OFF = 0;
    public static final int CLOCK_INTELIGENT_WEAKUP_TYPE_ON = 1;
    
    
    public static final int DND_CYCLE_TYPE_EVERYDAY = 0;
    public static final int DND_CYCLE_TYPE_WORKDAY = 1;
    
    
    public static final int DEVICE_MODE_UNWEAR = 6;
    public static final int DEVICE_MODE_STATIONARY = 0;
    public static final int DEVICE_MODE_WALK = 1;
    public static final int DEVICE_MODE_RUNNING = 2;
    
  
    //   remoteId  type
    public static final int DEVICE_REMOTEID_AIR = 0;
    public static final int DEVICE_REMOTEID_TV = 1;
    public static final int DEVICE_REMOTEID_BOX = 2;
    public static final int DEVICE_REMOTEID_CLEAN = 3;

}
