<script lang="tsx">
import { defineComponent, PropType, ref } from 'vue'
import { isHexColor } from '@/utils/color'
import { ElTag } from 'element-plus'
import { DictDataType, getDictOptions } from '@/utils/dictionary'

export default defineComponent({
  name: 'OnesTag',
  props: {
    type: {
      type: String as PropType<string>,
      required: true
    },
    value: {
      type: [String, Number, Boolean] as PropType<string | number | boolean>,
      required: true
    }
  },
  setup(props) {
    const dictData = ref<DictDataType>()
    const getDictObj = (dictCode: string, value: string) => {
      const options = getDictOptions(dictCode)
      options.forEach((item: DictDataType) => {
        if (item.value === value) {
          if (item.colorType + '' === 'default') {
            item.colorType = 'primary'
          }
          dictData.value = item
        }
      })
    }
    const rederDictTag = () => {
      if (!props.type) {
        return null
      }
      // 解决自定义字典标签值为零时标签不渲染的问题
      if (props.value === undefined || props.value === null) {
        return null
      }
      getDictObj(props.type, props.value.toString())
      // 添加标签的文字颜色为白色，解决自定义背景颜色时标签文字看不清的问题
      return (
        <ElTag
          style={dictData.value?.cssClass ? 'color: #fff' : ''}
          type={dictData.value?.colorType}
          color={
            dictData.value?.cssClass && isHexColor(dictData.value?.cssClass)
              ? dictData.value?.cssClass
              : ''
          }
          disableTransitions={true}
        >
          {dictData.value?.label}
        </ElTag>
      )
    }
    return () => rederDictTag()
  }
})
</script>
