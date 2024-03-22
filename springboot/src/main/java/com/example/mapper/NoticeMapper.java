package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Notice;
import java.util.List;

/**
 * 操作notice相关数据接口
*/
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
      * 新增
    */
    int M_insert(Notice notice);

    /**
      * 删除
    */
    int M_deleteById(Integer id);

    /**
      * 修改
    */
    int M_updateById(Notice notice);

    /**
      * 根据ID查询
    */
    Notice selectById(Integer id);

    /**
      * 查询所有
    */
    List<Notice> selectAll(Notice notice);

}