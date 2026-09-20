import type { Tag } from './Tag'

export interface Note {
  id: number
  title: string
  content: string
  isPinned: boolean
  isArchived: boolean
  tags: Tag[]
  createdAt: string
  updatedAt: string
}

export interface NoteQuery {
  keyword?: string
  tagId?: number
  isPinned?: boolean
  isArchived?: boolean
  page?: number
  size?: number
}

export interface NotePayload {
  title: string
  content: string
  tagIds?: number[]
}
