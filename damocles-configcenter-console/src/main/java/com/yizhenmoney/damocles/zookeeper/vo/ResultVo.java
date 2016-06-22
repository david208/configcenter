package com.yizhenmoney.damocles.zookeeper.vo;

/**
 * 
 * @author hezc
 *	返回结果值对象
 * 
 */
public class ResultVo {
	
	private int code;// 编码(0表示成功, -1表示未处理的异常)
	private Object attachment;
	public ResultVo(){
		super();
	}
	public ResultVo(int code, Object attachment) {
		this.code = code;
		this.attachment = attachment;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}
	

	public static ResultVo getResult(int code, Object attachment){
		return new ResultVo( code,  attachment);
	}
	
	public boolean getStatus(){
		return code==0?true:false;
	}
	
	public static class OK extends ResultVo {

		private OK() {
			super(1, null);
		}

		private OK(Object attachment) {
			super(1, attachment);
		}

	}
	
	public static class ExceptionVo extends ResultVo {

		private String exception;

		private ExceptionVo(String exception) {
			super(-1, exception);
			this.exception = exception;
		}

		private ExceptionVo(Exception e) {
			this(-1, e);
		}

		private ExceptionVo(int code, Exception e) {
			super(code, e);
			exception = e.getClass().getName();
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}
	}


	public static OK OK() {
		return new OK();
	}

	public static OK OK(Object attachment) {
		return new OK(attachment);
	}

	public static ExceptionVo error(String exception) {
		return new ExceptionVo(exception);
	}

	public static ExceptionVo error(Exception e) {
		return new ExceptionVo(e);
	}

	public static ExceptionVo error(int code, Exception e) {
		return new ExceptionVo(code, e);
	}
}
