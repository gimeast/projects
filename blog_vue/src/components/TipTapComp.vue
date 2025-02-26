<template>
  <div v-if="editor" class="container">
    <div class="control-group">
      <div class="button-group">
        <button type="button" @click="editor.chain().focus().toggleBold().run()" :disabled="!editor.can().chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
          Bold
        </button>
        <button type="button" @click="editor.chain().focus().toggleItalic().run()" :disabled="!editor.can().chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
          Italic
        </button>
        <button type="button" @click="editor.chain().focus().toggleStrike().run()" :disabled="!editor.can().chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
          Strike
        </button>
        <button type="button" @click="editor.chain().focus().toggleCode().run()" :disabled="!editor.can().chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
          Code
        </button>
        <button type="button" @click="editor.chain().focus().unsetAllMarks().run()">
          Clear marks
        </button>
        <button type="button" @click="editor.chain().focus().clearNodes().run()">
          Clear nodes
        </button>
        <button type="button" @click="editor.chain().focus().setParagraph().run()" :class="{ 'is-active': editor.isActive('paragraph') }">
          Paragraph
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
          H1
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
          H2
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
          H3
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 4 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
          H4
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 5 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
          H5
        </button>
        <button type="button" @click="editor.chain().focus().toggleHeading({ level: 6 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
          H6
        </button>
        <button type="button" @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
          Bullet list
        </button>
        <button type="button" @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
          Ordered list
        </button>
        <button type="button" @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }">
          Code block
        </button>
        <button type="button" @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }">
          Blockquote
        </button>
        <button type="button" @click="editor.chain().focus().setHorizontalRule().run()">
          Horizontal rule
        </button>
        <button type="button" @click="editor.chain().focus().setHardBreak().run()">
          Hard break
        </button>
        <button type="button" @click="editor.chain().focus().undo().run()" :disabled="!editor.can().chain().focus().undo().run()">
          Undo
        </button>
        <button type="button" @click="editor.chain().focus().redo().run()" :disabled="!editor.can().chain().focus().redo().run()">
          Redo
        </button>
      </div>
    </div>

    <editor-content :editor="editor" />
  </div>
</template>

<script setup>
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import { defineEmits, defineProps } from "vue"

const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  height: {
    type: Number,
    default: 150,
  },
})

const emit = defineEmits(['update:modelValue'])

const editor = useEditor({
  content: props.modelValue || "<p></p>",
  extensions: [StarterKit],
  onUpdate: ({ editor }) => {
    emit('update:modelValue', editor.getHTML())
  }
})
</script>

<style lang="scss">
.container {
  margin-top: 2rem;

  .control-group {
    background: white;
    border: 1px solid #e5e7eb;
    border-radius: 0.5rem;
    padding: 0.5rem;
    margin-bottom: 0.5rem;

    .button-group {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;

      button {
        background-color: white;
        border: 1px solid #e5e7eb;
        border-radius: 0.375rem;
        padding: 0.5rem 1rem;
        font-size: 0.875rem;
        color: #374151;
        cursor: pointer;
        transition: all 0.2s;

        &:hover {
          background-color: #f3f4f6;
        }

        &.is-active {
          background-color: #2563eb;
          color: white;
          border-color: #2563eb;
        }

        &:disabled {
          opacity: 0.5;
          cursor: not-allowed;
        }
      }
    }
  }

  .ProseMirror {
    background-color: white;
    border: 1px solid #e5e7eb;
    border-radius: 0.5rem;
    padding: 1rem;
    min-height: 200px;
    outline: none;

    &:focus {
      border-color: #2563eb;
      box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.1);
    }

    p.is-editor-empty:first-child::before {
      content: "내용을 입력하세요...";
      float: left;
      color: #9ca3af;
      pointer-events: none;
      height: 0;
    }

    > * + * {
      margin-top: 0.75em;
    }

    h1 { font-size: 2em; font-weight: bold; }
    h2 { font-size: 1.5em; font-weight: bold; }
    h3 { font-size: 1.17em; font-weight: bold; }
    h4 { font-size: 1em; font-weight: bold; }
    h5 { font-size: 0.83em; font-weight: bold; }
    h6 { font-size: 0.67em; font-weight: bold; }

    ul, ol {
      padding: 0 1rem;
    }

    ul { list-style-type: disc; }
    ol { list-style-type: decimal; }

    blockquote {
      border-left: 4px solid #e5e7eb;
      padding-left: 1rem;
      color: #4b5563;
    }

    code {
      background-color: #f3f4f6;
      padding: 0.2em 0.4em;
      border-radius: 0.25rem;
      font-family: monospace;
    }

    pre {
      background-color: #1f2937;
      color: #f3f4f6;
      padding: 1rem;
      border-radius: 0.5rem;
      overflow-x: auto;

      code {
        background: none;
        color: inherit;
        padding: 0;
      }
    }
  }
}
</style>