package com.hyzs.cidyth.module.reply.service.impl.validator;

import com.hyzs.cidyth.module.uc.common.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hyzs.cidyth.common.enums.ReplyTypeEnum;
import com.hyzs.cidyth.common.helper.ContextUserHelper;
import com.hyzs.cidyth.core.exception.ServiceException;
import com.hyzs.cidyth.module.reply.vo.Replies;
import com.hyzs.cidyth.module.uc.vo.Dept;
import com.hyzs.cidyth.module.uc.vo.User;
import com.hyzs.cidyth.module.websocket.MessageContentValidator;
/**
 * 针对信息的回复
 * @author derrick
 *
 */
@Service("replyContentOnInfoValidator")
public class ReplyContentOnInfoValidator implements MessageContentValidator<Replies> {
	private static final Logger logger = LoggerFactory.getLogger(ReplyContentOnInfoValidator.class);
	
	@Override
	public String name() {
		return ReplyTypeEnum.INFO.name();
	}

	@Override
	public void validate(Replies content) throws Exception {
		logger.debug("针对信息的回复进行验证...");
		if (content == null) {
			throw new ServiceException("参数不能为空!");
		}
		if (StringUtils.isBlank(content.getXxlx()) || !name().equals(content.getXxlx())) {
			throw new ServiceException("回复内容的类型不合法或者不是针对信息的回复!");
		}
		if (StringUtils.isBlank(content.getReferenceId())) {
			throw new ServiceException("缺少信息id.");
		}
		User user = UserUtil.getUser();
		if (StringUtils.isBlank(content.getFsry()) && (user == null)) {
			throw new ServiceException("缺少回复发起人用户编号");
		}
		if (StringUtils.isBlank(content.getFsry())) {
			content.setFsry(user.getAccount());
			content.setFsryxm(user.getName());
		}
		if (StringUtils.isBlank(content.getFsdw())) {
			Dept dept = user.getDepartment();
			if (dept != null) {
				content.setFsdw(dept.getCode());
				content.setFsdwmc(dept.getFullname());
			}
		}
		if (StringUtils.isBlank(content.getHfry())) {
			throw new ServiceException("缺少信息创建人编号");
		}
		if (StringUtils.isBlank(content.getFsnr())) {
			throw new ServiceException("没有回复内容!");
		}
		logger.debug("reply from User[" + content.getFsdw() + "," + content.getFsry() + "] to User[" + content.getHfry()
				+ "," + content.getHfdw() + "] on INFO[" + content.getReferenceId() + "]");
		
	}

}
