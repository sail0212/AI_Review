import { get, post, put, del } from './request'
import type { Theme, ThemePayload } from '@/types/Theme'

export const themeApi = {
  list: () => get<Theme[]>('/themes'),
  create: (data: ThemePayload) => post<Theme>('/themes', data),
  update: (id: number, data: ThemePayload) => put<Theme>(`/themes/${id}`, data),
  remove: (id: number) => del<void>(`/themes/${id}`),
}
