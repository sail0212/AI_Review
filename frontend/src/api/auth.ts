import { get, post } from './request'
import type { LoginResponse, User } from '@/types/User'

export interface LoginPayload {
  username: string
  password: string
}

export interface RegisterPayload {
  username: string
  password: string
  nickname?: string
}

export const authApi = {
  register: (data: RegisterPayload) => post<LoginResponse>('/auth/register', data),
  login: (data: LoginPayload) => post<LoginResponse>('/auth/login', data),
  me: () => get<User>('/auth/me'),
}
