<template>
<div>
    <div class="card" style="min-height: 80vh">
        <div class="blog-box" v-for="item in tableData" :key="item.id" v-if="total">
            <div style="flex: 1; width: 0">
                <!--                            @click="$router.push('/front/blogDetail?blogId='+item.id)"-->
                <a :href="'/front/blogDetail?blogId='+item.id" target="_blank">
                    <div class="blog-title">{{ item.title }}</div>
                </a>
                <div class="line1" style="color: #666 ;margin-bottom: 10px;font-size: 13px">
                    {{ item.descr }}
                </div>
                <div style="display: flex">
                    <div style="flex: 1;font-size: 13px">
                                    <span style="color: #666;margin-right: 20px"><i
                                            class="el-icon-user"></i> {{ item.userName }}</span>
                        <span style="color: #666;margin-right: 20px"><i
                                class="el-icon-eye"></i> {{ item.readCount }}</span>
                        <span style="color: #666;margin-right: 20px"><i
                                class="el-icon-like"></i> {{ item.likesCount }}</span>

                    </div>
                    <div style="width: fit-content">
                        <el-tag v-for="item in JSON.parse(item.tags || '[]')" :key="item" type="primary"
                                style="margin-right:5px">{{ item }}
                        </el-tag>
                    </div>
                </div>
            </div>
            <div style="width: 100px">
                <img style="width: 100%;border-radius: 5px;height: 80px" :src="item.cover" alt="">
            </div>
        </div>
        <div v-if="total === 0" style="padding: 20px 0; text-align: center; font-size: 16px; color: #666">
            暂无数据
        </div>
        <div style="margin-top: 10px" class="pagination" v-if="total">
            <el-pagination
                    background
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, prev, pager, next"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: "BlogList",
    props: {
        current:String,
        title:''
    },
    watch: {
        // 监听'current' prop的变化
        current() {
          this.loadBlogs(1);
        }
    },
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
        }
    },
    created() {
        this.loadBlogs(1)
    },
    methods: {
        loadBlogs(pageNum) {  // 分页查询 请求博客数据
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/blog/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    categoryName: this.current === '全部内容' ? null : this.current,
                    title: this.title === ''? null:this.title
                }
            }).then(res => {
                this.tableData = res.data?.list
                console.log(res.data)
                this.total = res.data?.total
            })
        },
        handleCurrentChange(pageNum) {
            this.loadBlogs(pageNum)
        },
    }
}
</script>

<style scoped>
.blog-box {
    display: flex;
    grid-gap: 15px;
    padding: 15px 0;
    border-bottom: 1px solid #ddd;
}

.blog-box:first-child {
    padding-top: 0;
}

.blog-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 10px;
    cursor: pointer;
}

.blog-title:hover {
    color: #2a60c9;
}
</style>