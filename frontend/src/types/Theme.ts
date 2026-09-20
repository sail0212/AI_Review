export interface Theme {
  id: number
  name: string
  config?: string
  isDefault: boolean
  createdAt?: string
}

export interface ThemePayload {
  name: string
  config?: string
  isDefault?: boolean
}
