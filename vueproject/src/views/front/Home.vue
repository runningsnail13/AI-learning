<template>
    <div class="main-content">
        <div style="display: flex;grid-gap: 10px; align-items: flex-start">
            <div style="width: 150px" class="card">
                <div class="category-item" :class="{'category-item-active':item.name === current}"
                     v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}
                </div>
            </div>

            <div style="flex: 1" class="card">

                <div class="blog-box" v-for="item in tableData" :key="item.id" v-if="total">
                    <div style="flex: 1; width: 0">
                        <div style="font-size: 16px; font-weight: bold;margin-bottom: 10px">{{item.title}}</div>
                        <div class="line1" style="color: #666 ;margin-bottom: 10px;font-size: 13px">
                            {{item.descr}}
                        </div>
                        <div style="display: flex">
                            <div style="flex: 1;font-size: 13px">
                                <span style="color: #666;margin-right: 20px"><i class="el-icon-user"></i> {{item.userName}}</span>
                                <span style="color: #666;margin-right: 20px"><i class="el-icon-eye"></i> {{item.readCount}}</span>
                                <span style="color: #666;margin-right: 20px"><i class="el-icon-like"></i> {{item.likeCount}}</span>

                            </div>
                            <div style="width: fit-content">
                                <el-tag type="primary" style="margin-right: 10px"></el-tag>
                            </div>
                        </div>
                    </div>
                    <div style="width: 100px">
                        <img style="width: 100%;border-radius: 5px;height: 80px"
                             :src="item.cover" alt="">
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


            <div style="width: 260px">
                <div class="card" style="margin-bottom: 10px">
                    <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px">欢迎访问！</div>
                    <a href="/front/person"><div style="color: #666">写篇文章分享你的知识库</div></a>
                </div>

                <div class="card" style="margin-bottom: 10px">
                    <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
                        <div style="font-size: 20px;flex: 1">文章榜单</div>
                        <div style="font-size: 12px;color: #666;cursor: pointer" @click="refreshTop"><i class="el-icon-refresh"></i> 换一批</div>
                    </div>
                    <div>
                        <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
              <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
                <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
                <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
                <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
                <span style="color: #666" v-else>{{ item.index }}</span>
              </span>
                            <span style="color: #666;">{{ item.title }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {

    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            current: '全部内容',
            categoryList: [],
            topList: [],
            showList: [],
            lastIndex: 0,
            topActivityList: [],
        }
    },
    mounted() {
        this.load()
        this.loadBlogs(1)
        this.refreshTop()
    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
        refreshTop(){
            this.$request.get('/blog/selectTop').then(res => {
                this.topList = res.data || []
                let i = 1
                this.topList.forEach(item => item.index = i++)

                // 0  5  0
                if (this.lastIndex === 20) {
                    this.lastIndex = 0
                }
                this.showList = this.topList.slice(this.lastIndex, this.lastIndex+5)  // 0-5   5- 10  // 0-5
                this.lastIndex += 5  // 5  10  5
            })
        },
        load() {
            //请求分类数据
            this.$request.get('category/selectAll').then(res => {
                this.categoryList = res.data || []
                this.categoryList.unshift({name: '全部内容'})
            })
        },
        loadBlogs(pageNum) {  // 分页查询 请求博客数据
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/blog/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    categoryName: this.current === '全部内容' ? null : this.current,
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
        selectCategory(categoryName) {
            this.current = categoryName
            this.loadBlogs(1)
        }
    }
}
</script>
<style>
.category-item {
    text-align: center;
    padding: 10px 0;
    font-size: 16px;
    cursor: pointer;
}

.category-item-active {
    background-color: #1890ff;
    color: #fff;
    border-radius: 5px;
}

.line1 {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.blog-box{
    display: flex;
    grid-gap: 15px;
    padding: 15px 0;
    border-bottom: 1px solid #ddd;
}
.blog-box:first-child{
    padding-top: 0;
}
</style>