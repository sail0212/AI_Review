import { get, post, put, del } from './request'
import type { Share, ShareCreatePayload } from '@/types/Share'
import type { Note, NotePayload } from '@/types/Note'

export const shareApi = {
  create: (noteId: number, data?: ShareCreatePayload) => post<Share>(`/notes/${noteId}/share`, data),
  listByNote: (noteId: number) => get<Share[]>(`/notes/${noteId}/shares`),
  revoke: (id: number) => del<void>(`/shares/${id}`),
  getByToken: (token: string) => get<Note>(`/share/${token}`),
  updateByToken: (token: string, data: NotePayload) => put<Note>(`/share/${token}`, data),
}
