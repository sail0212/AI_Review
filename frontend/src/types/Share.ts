export type ShareMode = 'readonly' | 'editable'

export interface Share {
  id: number
  token: string
  mode: ShareMode
  expiresAt?: string
  url: string
}

export interface ShareCreatePayload {
  mode?: ShareMode
  expiresAt?: string
}
