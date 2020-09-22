package com.yitongyin.modules.ad.msg;


import com.yitongyin.modules.ad.form.Project;
import com.yitongyin.modules.ad.inti.SpringContextSupport;

public class MsgSenderFactory {
	
	private Project project;
	
	private MsgSenderFactory(){project=SpringContextSupport.getBean(Project.class);}
	
	public static MsgSenderFactory getInstance(){
		return single.single;
	}
	
	public MsgSender getSender(){
		return (MsgSender) SpringContextSupport.getBean(project.getMsgSender());
	}
	
	private static final class single{
		public static final MsgSenderFactory single = new MsgSenderFactory();
	}

}
