import { Routes } from '@angular/router';
import { MessageListComponent } from './components/message-list/message-list.component';
import { PartnersListComponent } from './components/partners/partners-list/partners-list.component';
import { PartnerFormComponent } from './components/partners/partner-form/partner-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'messages', pathMatch: 'full' },
  { path: 'messages', component: MessageListComponent }, 
  { 
    path: 'partners', 
    children: [
      { path: '', component: PartnersListComponent },
      { path: 'new', component: PartnerFormComponent },
      { path: 'edit/:id', component: PartnerFormComponent }
    ]
  },
  { path: '**', redirectTo: '/messages' }
];