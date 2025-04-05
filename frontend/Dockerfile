# Development stage
FROM node:18-alpine

WORKDIR /app

# Install dependencies first for better caching
COPY package.json package-lock.json ./
RUN npm install

# Copy remaining files
COPY . .

# Expose Angular dev server port
EXPOSE 4200

# Start Angular dev server with host 0.0.0.0
CMD ["npm", "start", "--", "--host", "0.0.0.0", "--port", "4200"]
