<template>
  <div v-if="editor" class="tiptap-container">
    <div class="control-group">
      <TipTapToolbarComp :editor="editor"/>
    </div>

    <editor-content :editor="editor"/>
  </div>
</template>

<script setup>
import {useEditor, EditorContent} from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import {defineEmits, defineProps, ref, watch} from "vue"
import TipTapToolbarComp from "@/components/TipTapToolbarComp.vue";

const content = ref('')
const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['update:modelValue'])

const editor = useEditor({
  content: content.value || "<p></p>",
  extensions: [StarterKit],
  onUpdate: ({editor}) => {
    emit('update:modelValue', editor.getHTML())
  }
})

watch(() => props.modelValue, (newValue) => {
  if (editor.value && newValue !== editor.value.getHTML()) {
    editor.value.commands.setContent(newValue)
  }
})

</script>

<style lang="scss">
.tiptap-container {
  margin-top: 2rem;

  .control-group {
    background: white;
    border: 1px solid #e5e7eb;
    border-radius: 0.5rem;
    padding: 0.5rem;
    margin-bottom: 0.5rem;
  }
}

</style>