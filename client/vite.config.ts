import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { env } from "process"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/modelr": {
        target: env["BACKEND_SERVER"] ?? "http://localhost:8080",
        changeOrigin: true,
        secure: false
      }
    },
    host: true
  }
})
