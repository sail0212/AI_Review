export interface User {
  id: number
  username: string
  nickname?: string
  avatar?: string
}

export interface LoginResponse {
  token: string
  userId: number
  username: string
  nickname?: string
}
