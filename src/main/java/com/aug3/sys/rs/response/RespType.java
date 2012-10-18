package com.aug3.sys.rs.response;

public enum RespType {
	
	PARAMETEREXCEPTION("10199"),
	
	/*
	 * 正常返回code：200-299
	 * 重定向code：300-399
	 * 客户端错误code：400-499
	 * 服务器错误code：500-599
	 * 通用业务code：10100-10199
	 * 权限code：10200-10299
	 * oauth认证code：10300-10399
	 */
	SUCCESS("200"),
	
	SUCCESS_CREATED("201"),
	
	//The request has been accepted for processing, but the processing has not been completed
	SUCCESS_ACCEPTED("202"),
	
	SUCCESS_NO_CONTENT("204"),
	
	REDIRECT_PERMANTENTLY("301"),
	
	REDIRECT_PROXY_REQUIRED("305"),
	
	REDIRECT_TEMMPORARILY("307"),
	
	BAD_REQUEST("400"),
	
	UNAUTHORIZED_REQUEST("401"),
	
	FORBIDDEN_REQUEST("403"),
	
	NOT_FOUND("404"),
	
	METHOD_NOT_ALLOWED("405"),
	
	PROXY_UNAUTHORIZED_REQUEST("407"),
	
	REQUEST_TIMEOUT("408"),	
	
	INTERNAL_SERVER_ERROR("500"),
	
	BAD_GATEWAY("502"),
	
	SERVICE_UNAVAILABLE("503"),
	
	GATEWAY_TIMEOUT("504"),
	
	HTTP_VERSION_NOT_SUPPORTED("505"),
	
	IO_EXCEPTION("10100"),
	
	IO_TIMEOUT("10101"),
	
	NULL_PARAMETERS("10102"),
	
	INCORRECT_TYPE_PARAMETERS("10103"),
	
	PARAMETERS_PARSING_EXCEPTION("10104"),
	
	INVALID_PARAMETERS("10105"),
	
	ENCODING_EXCEPTION("10106"),
	
	RUNTIME_EXCEPTION("10107"),
	
	NO_PERMISSION("10200"),
	
	DOWNLOAD_TIMES_LIMIT("10201"),
	
	DOWNLOAD_FLOW_LIMIT("10202"),
	
	INVALID_APP("10300"),
	
	UNAUTHORIZED_APP("10301"),
	
	EXPIRED_APP("10302"),
	
	EXPIRED_ACCESS_TOKEN("10303"),
	
	INVALID_TIMESTAMP("10304"),
	
	NONCE_REJECT("10305"),
	
	BAD_REQUEST_MOTHOD("10306"),
	
	BAD_SIGN_METHOD("10307"),
	
	INVALID_SIGNATURE("10308"),
	
	;
	
	private String code;
	
	private RespType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}

