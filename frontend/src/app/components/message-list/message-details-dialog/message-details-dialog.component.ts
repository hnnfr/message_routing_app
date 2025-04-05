import { Component, Inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { 
  MAT_DIALOG_DATA, 
  MatDialogModule, 
  MatDialogRef 
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

import { Message } from '../../../models/message.model';

@Component({
  selector: 'app-message-details-dialog',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatDividerModule,
    MatIconModule, 
    MatListModule
  ],
  templateUrl: './message-details-dialog.component.html',
  styleUrls: ['./message-details-dialog.component.scss']
})
export class MessageDetailsDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<MessageDetailsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public message: Message
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}