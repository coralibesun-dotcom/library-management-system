<template>
  <div class="app-container">
    <!-- 顶部：只有一个"新增分类"按钮（分类不分页、不搜索） -->
    <div style="margin-bottom: 15px">
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">新增分类</el-button>
    </div>

    <!-- 表格：4 列。注意 :data 直接绑 list，没有分页器 -->
    <el-table
      v-loading="listLoading"
      :data="list"
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
  created() {
    this.getList()
  },
  methods: {
    // ↓↓↓ 以下 5 个方法由你来填，照着 book/index.vue 的套路改 ↓↓↓

    // 1. 拉取列表：调 getAllCategories()，注意它返回的是 res.data（不是 res.data.records）
    async getList() {
      // TODO: 1) 开 loading  2) await getAllCategories()  3) this.list = res.data  4) 关 loading
      this.listLoading = true
      const res = await getAllCategories()
      this.list = res.data
      this.listLoading = false
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
