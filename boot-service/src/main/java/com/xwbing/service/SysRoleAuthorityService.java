package com.xwbing.service;

import com.xwbing.entity.SysRoleAuthority;
import com.xwbing.repository.SysRoleAuthorityRepository;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 15:15
 * 作者: xiangwb
 * 说明:
 */
@Service
public class SysRoleAuthorityService {
    @Resource
    private SysRoleAuthorityRepository sysRoleAuthorityRepository;

    /**
     * 执行用户角色权限保存操作,保存之前先判断是否存在，存在删除
     *
     * @param list
     * @param roleId
     * @return
     */
    public RestMessage saveBatch(List<SysRoleAuthority> list, String roleId) {
        RestMessage result = new RestMessage();
        //获取角色原有权限
        List<SysRoleAuthority> roleAuthorities = listByRoleId(roleId);
        //删除原有权限
        if (CollectionUtils.isNotEmpty(roleAuthorities)) {
            sysRoleAuthorityRepository.deleteInBatch(roleAuthorities);
        }
        //角色新增权限
        list.forEach(roleAuthority -> {
            roleAuthority.setId(PassWordUtil.createId());
            roleAuthority.setModifiedTime(new Date());
        });
        List<SysRoleAuthority> save = sysRoleAuthorityRepository.save(list);
        if (CollectionUtils.isNotEmpty(save)) {
            result.setSuccess(true);
            result.setMessage("保存角色权限成功");
        } else {
            result.setMessage("保存角色权限失败");
        }
        return result;
    }


    /**
     * 根据角色主键获取
     *
     * @param roleId
     * @return
     */
    public List<SysRoleAuthority> listByRoleId(String roleId) {
        return sysRoleAuthorityRepository.getByRoleId(roleId);
    }
}
