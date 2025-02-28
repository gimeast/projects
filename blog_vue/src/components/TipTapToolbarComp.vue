<template>
  <div class="button-group">
    <button type="button" @click="editor.chain().focus().toggleBold().run()"
            :disabled="!editor.can().chain().focus().toggleBold().run()"
            :class="{ 'is-active': editor.isActive('bold') }">
      Bold
    </button>
    <button type="button" @click="editor.chain().focus().toggleItalic().run()"
            :disabled="!editor.can().chain().focus().toggleItalic().run()"
            :class="{ 'is-active': editor.isActive('italic') }">
      Italic
    </button>
    <button type="button" @click="editor.chain().focus().toggleStrike().run()"
            :disabled="!editor.can().chain().focus().toggleStrike().run()"
            :class="{ 'is-active': editor.isActive('strike') }">
      Strike
    </button>
    <button type="button" @click="editor.chain().focus().toggleCode().run()"
            :disabled="!editor.can().chain().focus().toggleCode().run()"
            :class="{ 'is-active': editor.isActive('code') }">
      Code
    </button>
    <button type="button" @click="editor.chain().focus().unsetAllMarks().run()">
      Clear marks
    </button>
    <button type="button" @click="editor.chain().focus().clearNodes().run()">
      Clear nodes
    </button>
    <button type="button" @click="editor.chain().focus().setParagraph().run()"
            :class="{ 'is-active': editor.isActive('paragraph') }">
      Paragraph
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
      H1
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
      H2
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
      H3
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 4 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
      H4
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 5 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
      H5
    </button>
    <button type="button" @click="editor.chain().focus().toggleHeading({ level: 6 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
      H6
    </button>
    <button type="button" @click="editor.chain().focus().toggleBulletList().run()"
            :class="{ 'is-active': editor.isActive('bulletList') }">
      Bullet list
    </button>
    <button type="button" @click="editor.chain().focus().toggleOrderedList().run()"
            :class="{ 'is-active': editor.isActive('orderedList') }">
      Ordered list
    </button>
    <button type="button" @click="editor.chain().focus().toggleCodeBlock().run()"
            :class="{ 'is-active': editor.isActive('codeBlock') }">
      Code block
    </button>
    <button type="button" @click="editor.chain().focus().toggleBlockquote().run()"
            :class="{ 'is-active': editor.isActive('blockquote') }">
      Blockquote
    </button>
    <button type="button" @click="editor.chain().focus().setHorizontalRule().run()">
      Horizontal rule
    </button>
    <button type="button" @click="editor.chain().focus().setHardBreak().run()">
      Hard break
    </button>
    <button type="button" @click="editor.chain().focus().undo().run()"
            :disabled="!editor.can().chain().focus().undo().run()">
      Undo
    </button>
    <button type="button" @click="editor.chain().focus().redo().run()"
            :disabled="!editor.can().chain().focus().redo().run()">
      Redo
    </button>
  </div>
</template>

<script setup>
import {Editor} from '@tiptap/vue-3'
import {defineProps} from 'vue'

defineProps({
  editor: {
    type: Editor,
    required: true
  }
})
</script>

<style lang="scss">
.button-group {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;

  button {
    padding: 0.5rem;
    border: 1px solid #e5e7eb;
    border-radius: 0.25rem;
    background: white;

    &:hover {
      background: #f3f4f6;
    }

    &.is-active {
      background: #e5e7eb;
    }

    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}

</style>