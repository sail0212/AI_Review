import { get, post, put, patch, del } from './request'
import type { Note, NotePayload, NoteQuery } from '@/types/Note'
import type { PageResult } from '@/types/PageResult'

export const noteApi = {
  list: (query?: NoteQuery) => get<PageResult<Note>>('/notes', query),
  detail: (id: number) => get<Note>(`/notes/${id}`),
  create: (data: NotePayload) => post<Note>('/notes', data),
  update: (id: number, data: NotePayload) => put<Note>(`/notes/${id}`, data),
  remove: (id: number) => del<void>(`/notes/${id}`),
  togglePin: (id: number) => patch<Note>(`/notes/${id}/pin`),
  toggleArchive: (id: number) => patch<Note>(`/notes/${id}/archive`),
}
