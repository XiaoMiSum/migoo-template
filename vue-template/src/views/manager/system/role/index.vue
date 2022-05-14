<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="角色名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入角色名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="角色标识" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入角色标识"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="角色状态" clearable size="small" style="width: 240px">
          <el-option v-for="(item, index) in COMMON_STATUS_ENUMS" :key="index" :value="item.key" :label="item.label" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="roleList">
      <el-table-column label="角色编号" prop="id" width="120" />
      <el-table-column label="角色名称" prop="name" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="角色标识" prop="code" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="角色类型" prop="type" :formatter="typeFormat" width="80" />
      <el-table-column label="显示顺序" prop="sort" width="100" />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" :disabled="scope.row.type === 1" @change="handleStatusChange(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:role:edit']"
            size="small"
            :disabled="scope.row.type === 1"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['system:permission:assign-role-menu']"
            size="small"
            :disabled="scope.row.type === 1"
            type="text"
            icon="el-icon-circle-check"
            @click="handleMenu(scope.row)"
          >菜单权限</el-button>
          <el-button
            v-hasPermi="['system:role:remove']"
            size="small"
            :disabled="scope.row.type === 1"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色标识" prop="code">
          <el-input v-model="form.code" placeholder="请输入角色标识" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 分配角色的菜单权限对话框 -->
    <el-dialog :title="title" :visible.sync="openMenu" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="form.name" :disabled="true" />
        </el-form-item>
        <el-form-item label="角色标识">
          <el-input v-model="form.code" :disabled="true" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
          <el-tree
            ref="menu"
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            node-key="id"
            :check-strictly="form.menuCheckStrictly"
            empty-text="加载中，请稍后"
            :props="defaultProps"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelMenu">取 消</el-button>
        <el-button type="primary" @click="submitMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addData,
  updateData,
  delData,
  getData,
  listData
} from '@/api/manager/role'
import { listSimpleMenus } from '@/api/manager/menu'
import { assignRoleMenu, listRoleMenus } from '@/api/manager/permission'
import { COMMON_STATUS_ENUM, COMMON_STATUS_ENUMS } from '@/utils/enums'

export default {
  name: 'Role',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（菜单权限）
      openMenu: false,
      menuExpand: false,
      menuNodeAll: false,
      // 菜单列表
      menuOptions: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: undefined,
        code: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '角色标识不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '角色顺序不能为空', trigger: 'blur' }
        ]
      },
      COMMON_STATUS_ENUMS: COMMON_STATUS_ENUMS
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true
      listData(this.queryParams).then(
        response => {
          this.roleList = response.data.list
          this.total = response.data.total
          this.loading = false
        }
      )
    },
    // 角色状态修改
    handleStatusChange(row) {
      // 此时，row 已经变成目标状态了，所以可以直接提交请求和提示
      const text = row.status === COMMON_STATUS_ENUM.ENABLE ? '启用' : '停用'
      this.$confirm('确认要"' + text + '""' + row.name + '"角色吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return updateData(row.id, row.status)
      }).then(() => {
        this.msgSuccess(text + '成功')
      }).catch(function() {
        // 异常时，需要将 row.status 状态重置回之前的
        row.status = row.status === COMMON_STATUS_ENUM.ENABLE ? COMMON_STATUS_ENUM.DISABLE
          : COMMON_STATUS_ENUM.ENABLE
      })
    },
    // 角色类型字典翻译
    typeFormat(row, column) {
      return row.type === 1 ? '内置' : '自定义'
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 取消按钮（菜单权限）
    cancelMenu() {
      this.openMenu = false
      this.reset()
    },
    // 表单重置
    reset() {
      if (this.$refs.menu !== undefined) {
        this.$refs.menu.setCheckedKeys([])
      }
      this.menuExpand = false
      this.menuNodeAll = false
      this.form = {
        id: undefined,
        name: undefined,
        code: undefined,
        sort: 0,
        menuIds: [],
        dataScope: undefined,
        menuCheckStrictly: true,
        remark: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      const treeList = this.menuOptions
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      this.$refs.menu.setCheckedNodes(value ? this.menuOptions : [])
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加角色'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getData(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改角色'
      })
    },
    /** 分配菜单权限操作 */
    handleMenu(row) {
      this.reset()
      const id = row.id
      // 处理了 form 的角色 name 和 code 的展示
      this.form.id = id
      this.form.name = row.name
      this.form.code = row.code
      // 打开弹窗
      this.openMenu = true
      // 获得菜单列表
      listSimpleMenus().then(response => {
        // 处理 menuOptions 参数
        this.menuOptions = []
        this.menuOptions.push(...this.handleTree(response.data, 'id'))
      })
      // 获得角色拥有的菜单集合
      listRoleMenus(id).then(response => {
        // 设置为严格，避免设置父节点自动选中子节点，解决半选中问题
        this.form.menuCheckStrictly = true
        // 设置选中
        this.$refs.menu.setCheckedKeys(response.data)
        // 设置为非严格，继续使用半选中
        this.form.menuCheckStrictly = false
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            updateData(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addData(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 提交按钮（菜单权限） */
    submitMenu: function() {
      if (this.form.id !== undefined) {
        assignRoleMenu(
          this.form.id,
          { menuIds: [...this.$refs.menu.getCheckedKeys(), ...this.$refs.menu.getHalfCheckedKeys()] }
        ).then(response => {
          this.msgSuccess('修改成功')
          this.openMenu = false
          this.getList()
        })
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除角色编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delData(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    }
  }
}
</script>
