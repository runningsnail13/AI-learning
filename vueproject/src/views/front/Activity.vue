<template>
    <div class="main-content">
        <el-row :gutter="10">
            <el-col :span="6" v-for="item in tableData" :key="item.id">
                <div class="card" style="margin-bottom: 10px" @click="goDetail(item.id)">
                    <img :src="item.cover" alt="" style="width: 100%; height: 150px; border-radius: 5px">
                    <div style="margin: 10px 0; font-weight: bold" class="line1">{{ item.name }}</div>
                    <div style="display: flex; align-items: center">
                        <div style="flex: 1; color: #666"><i class="el-icon-date"></i> {{ item.start }}</div>
                        <el-button type="primary" disabled v-if="item.isEnd" key="已结束">已结束</el-button>
                        <el-button type="primary" v-if="!item.isEnd && !item.isSign" key="未报名">未报名</el-button>
                        <el-button type="primary" disabled v-if="!item.isEnd && item.isSign"  key="已报名">已报名</el-button>
                    </div>
                </div>
            </el-col>
        </el-row>

        <div class="pagination" style="margin-top: 10px">
            <el-pagination
                    background
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-size="pageSize"
                    layout="total, prev, pager, next"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "Activity",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 8,  // 每页显示的个数
            total: 0,
            name: null,
        }
    },
    created() {
        this.load(1)
    },
    methods: {
        goDetail(id) {
            window.open('/front/activityDetail?activityId=' + id)
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/activity/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                this.tableData = res.data?.list
                this.total = res.data?.total
            })
        },
        reset() {
            this.name = null
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
    }
}
</script>

<style scoped>

</style>