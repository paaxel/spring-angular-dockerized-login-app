export const environment = {
  production: true,
  API_URL: window["env"]["apiUrl"] || "default",

  frontend_version: '1.0.0',
  AUTH_TOKEN_KEY: 'auth_token',

  LOGIN_PATH: 'authentication',
  PROFILE_WS_URL: 'ws'
};
