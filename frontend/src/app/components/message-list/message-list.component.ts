import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';
import { DatePipe } from '@angular/common';
import { TruncatePipe } from '../../shared/truncate.pipe';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [ MatTableModule, MatPaginator, MatCardModule, DatePipe, MatProgressSpinnerModule, TruncatePipe, RouterModule ],
  templateUrl: './message-list.component.html',
  styleUrl: './message-list.component.scss'
})
export class MessageListComponent implements OnInit {
  dataSource = new MatTableDataSource<Message>();
  displayedColumns: string[] = ['id', 'messageId', 'content', 'correlationId', 'receivedTimestamp'];
  pagination = {
    currentPage: 0,
    totalItems: 0,
    totalPages: 0,
    pageSize: 20
  };
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private messageService: MessageService) { }

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(page: number = 0, size: number = 20): void {
    this.isLoading = true;
    this.messageService.getMessages(page, size).subscribe({
      next: (response) => {
        this.dataSource.data = response.content;
        this.pagination = {
          currentPage: response.pageNumber,
          totalItems: response.totalElements,
          totalPages: response.totalPages,
          pageSize: response.pageSize
        };
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading messages', err);
        this.isLoading = false;
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.loadMessages(event.pageIndex, event.pageSize);
  }
}