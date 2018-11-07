package com.swapping.springcloud.ms.core.base;

import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.utils.SwappingUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础bean
 * 所有实体 都可以有这些基础字段
 *
 */
@MappedSuperclass
public class BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键 自增

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, updatable = false)
    private Date createDate;// 创建时间

    private Date updateDate;// 修改时间

    private String updateId; // 修改人

    @Column(nullable = false)
    private String createId; // 创建人


    @Column(nullable = false)
    private String uid;     //业务主键

    @Transient
    private Integer pageNum = 0;    //分页 页号

    @Transient
    private Integer pageSize = 10;  //分页 页量


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void initEntity(){
        this.createDate = new Date();
        this.createId = SwappingUtils.uid();//如果有当前登陆人，则初始化为当前登陆人
        this.uid = SwappingUtils.uid();
    }

    public void updateEntity(){
        this.updateDate = new Date();
        this.updateId = SwappingUtils.uid();//如果有当前登陆人，则赋值为当前登陆人
    }

    /**
     * 分页 工具方法
     * @return
     */
    public PageRequest page(){
        return PageRequest.of(pageNum,this.pageSize);
    }

    /**
     * 默认按照  创建日期降序排序
     *
     * 因为 spring boot 2.0版本
     * new PageRequest（）做法已经不推荐使用了
     * @return
     */
    public PageRequest page2(){
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return new PageRequest(this.pageNum,this.pageSize,sort);
    }

    /**
     * 分页 排序工具方法
     *
     * 中文字段排序 需要在查询出来后处理 sort无法解决中文排序的问题
     * @param map
     * @param obj
     * @return
     * @throws Exception
     */
    public PageRequest page(Map<String,Sort.Direction> map, Object obj) throws MyException{
        //反射获取实体所有属性
        List<String> properties = Arrays.stream(obj.getClass().getDeclaredFields()).map(i->i.getName()).collect(Collectors.toList());
        Set<String> keySet = map.keySet();
        Sort sort = null;
        if (properties.containsAll(keySet)){
            for (String str:keySet){
                if (sort == null){
                    sort = Sort.by(map.get(str),str);
                }else{
                    sort = sort.and(Sort.by(map.get(str),str));
                }
            }
        }else{
            throw new MyException("排序字段非本实体字段");
        }
        return PageRequest.of(this.pageNum,this.pageSize,sort);
    }
}
