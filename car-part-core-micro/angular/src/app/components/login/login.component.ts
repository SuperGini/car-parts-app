import {Component} from '@angular/core';
import {MatButton, MatFabButton} from '@angular/material/button';

@Component({
  selector: 'login-component',
  templateUrl: './login.html',
  imports: [

  ],
  styleUrl: './login.scss'
})
export class LoginComponent {

  redirectToKeycloakLoginPage() {
    window.location.href = redirectKeycloakURL();
  }
  
}

const redirectKeycloakURL = () => {
  return 'http://localhost:8080/realms/GINI/protocol/openid-connect/auth?response_type=code&client_id=core-micro&redirect_uri=http://localhost:9090/autorize&scope=openid';
}
