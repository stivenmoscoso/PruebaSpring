# --- Etapa 1: Construcción/Dependencias ---
FROM node:20-alpine AS builder
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./
RUN npm ci

# Copiar el resto del código y construir (si usas TypeScript/Bundlers)
COPY . .
# RUN npm run build # Descomenta si aplica

# --- Etapa 2: Producción ---
FROM node:20-alpine AS runner
WORKDIR /app

# Variable de entorno por defecto
ENV NODE_ENV=production

# Copiar solo lo necesario desde la etapa de compilación
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app .

# Exponer el puerto del backend
EXPOSE 3000

# Ejecutar la aplicación como usuario no-root (Seguridad)
USER node

CMD ["npm", "start"]