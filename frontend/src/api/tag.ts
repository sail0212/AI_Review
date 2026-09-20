import { get, post, put, del } from './request'
import type { Tag } from '@/types/Tag'

export const tagApi = {
  list: () => get<Tag[]>('/tags'),
  create: (name: string) => post<Tag>('/tags', { name }),
  rename: (id: number, name: string) => put<Tag>(`/tags/${id}`, { name }),
  remove: (id: number) => del<void>(`/tags/${id}`),
}
