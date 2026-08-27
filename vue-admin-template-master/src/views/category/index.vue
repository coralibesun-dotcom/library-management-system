<template>
  <div class="app-container">
    <!-- 顶部：只有一个"新增分类"按钮（分类不分页、不搜索） -->
    <div style="margin-bottom: 15px">
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">新增分类</el-button>
    </div>

    <!-- 表格：4 列。:data 绑的是 computed 切出来的当前页，不是整个 list -->
    <el-table
      v-loading="listLoading"
      :data="pagedList"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" prop="id" width="80" align="center" />

      <el-table-column label="分类名" prop="name" min-width="200" show-overflow-tooltip />

      <el-table-column label="创建时间" prop="createTime" min-width="180" align="center" />

      <el-table-column label="操作" width="160" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器：前端分页，total 直接用 list.length，翻页不发请求 -->
    <el-pagination
      style="margin-top: 15px"
      background
      layout="total, prev, pager, next"
      :total="list.length"
      :page-size="listQuery.pageSize"
      :current-page="listQuery.pageNum"
      @current-change="handlePageChange"
    />

    <!-- Dialog：只有一个"分类名"字段 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="450px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getAllCategories, addCategory, updateCategory, deleteCategory } from '@/api/category'
export default {
  data() {
    return {
      list: [],
      listLoading: true,
      // 前端分页参数：数据已经全量在 list 里，只记录"当前看到第几页"
      listQuery: {
        pageNum: 1,
        pageSize: 10
      },
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: undefined,
        name: ''
      },
      rules: {
        name: [{ required: true, message: '请输入分类名', trigger: 'blur' }]
      }
    }
  },
  computed: {
    // 前端分页核心：把整份 list 按当前页码切成 10 条
    pagedList() {
      const start = (this.listQuery.pageNum - 1) * this.listQuery.pageSize
      const end = start + this.listQuery.pageSize
      return this.list.slice(start, end)
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // ↓↓↓ 以下 5 个方法由你来填，照着 book/index.vue 的套路改 ↓↓↓

    // 1. 拉取列表：仍全量拉（分类天生就少），拉完校正页码防止停在空页
    async getList() {
      this.listLoading = true
      const res = await getAllCategories()
      this.list = res.data
      // 删除最后一条后页码可能"悬空"（第2页只剩0条），自动退回最后一页
      const maxPage = Math.max(1, Math.ceil(this.list.length / this.listQuery.pageSize))
      if (this.listQuery.pageNum > maxPage) {
        this.listQuery.pageNum = maxPage
      }
      this.listLoading = false
    },

    // 翻页：前端分页不发请求，只改页码，pagedList 自动重算
    handlePageChange(pageNum) {
      this.listQuery.pageNum = pageNum
    },

    // 2. 新增：打开弹窗前把 form 洗成空白（id: undefined, name: ''）
    handleAdd() {
      // TODO
      this.dialogTitle = '新增分类'
      this.form = {
        id: undefined,
        name: ''
      }
      this.dialogVisible = true
    },

    // 3. 编辑：用 { ...row } 浅拷贝行数据进 form--只对弹窗内容进行改变
    handleEdit(row) {
      // TODO
      this.dialogTitle = '编辑分类'
      this.form = { ...row }
      this.dialogVisible = true
    },

    // 4. 提交：先 $refs.form.validate，再按 this.form.id 有无决定走 addCategory 还是 updateCategory
    submitForm() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        if (this.form.id) {
          await updateCategory(this.form)
          this.$message.success('修改成功')
        } else {
          await addCategory(this.form)
          this.$message.success('新增成功')
        }
        this.dialogVisible = false
        this.getList()
      })
    },

    // 5. 删除：$confirm 三件套（.then 里 await deleteCategory + 提示 + getList），别忘了 .catch(()=>{})
    handleDelete(row) {
      this.$confirm('确定要删除分类「' + row.name + '」吗？', '提示', { type: 'warning' })
        .then(async() => {
          await deleteCategory(row.id)
          this.$message.success('删除成功')
          this.getList()
        })
        .catch(() => {})
    }
  }
}
</script>
