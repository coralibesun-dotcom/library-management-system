<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <div style="margin-bottom: 20px">
      <el-input
        v-model="listQuery.operation"
        placeholder="搜索操作类型"
        style="width: 200px"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" border style="width: 100%">
      <el-table-column label="ID" prop="id" width="80" align="center" />
      <el-table-column label="用户ID" prop="userId" width="100" align="center" />
      <el-table-column label="操作类型" prop="operation" width="180" align="center" />
      <el-table-column label="操作对象" prop="target" min-width="200" show-overflow-tooltip />
      <el-table-column label="操作时间" prop="createTime" width="180" align="center" />
    </el-table>

    <!-- 分页器 -->
    <el-pagination
      style="margin-top: 20px; text-align: center"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageSize"
      :total="total"
      :page-sizes="[10, 20, 50]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import { getLogPage } from '@/api/log'

export default {
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        operation: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      this.listLoading = true
      const res = await getLogPage(this.listQuery)
      this.list = res.data.records
      this.total = res.data.total
      this.listLoading = false
    },

    handleSearch() {
      this.listQuery.pageNum = 1
      this.getList()
    },

    handlePageChange(val) {
      this.listQuery.pageNum = val
      this.getList()
    },

    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    }
  }
}
</script>
