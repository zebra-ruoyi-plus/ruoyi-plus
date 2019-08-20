package com.zebra.bussiness.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zebra.common.core.domain.BaseEntity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * API权限认证表 t_api_security
 * 
 * @author zebra
 * @date 2019-08-20
 */
@Table(name="t_api_security")
@Getter
@Setter
public class ApiSecurity extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** api接口服务key */
 	@Id
 	@Column(name="api_key")
	private String apiKey;
	
	/** API接口服务secret */
 	@Column(name="api_secret")
	private String apiSecret;
	
	/** api接口服务状态1正常 0禁用 */
 	@Column(name="api_status")
	private Boolean apiStatus;
	
	/** API接口服务类型 1pc2公众号3小程序4移动端5APP */
 	@Column(name="api_type")
	private Integer apiType;
	
	/**  */
 	@Column(name="api_create_time")
	private Date apiCreateTime;
	
	/**  */
 	@Column(name="api_update_time")
	private Date apiUpdateTime;
	

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("apiKey", getApiKey())
            .append("apiSecret", getApiSecret())
            .append("apiStatus", getApiStatus())
            .append("apiType", getApiType())
            .append("apiCreateTime", getApiCreateTime())
            .append("apiUpdateTime", getApiUpdateTime())
            .toString();
    }
}
