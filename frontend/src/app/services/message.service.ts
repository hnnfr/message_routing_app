import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message, MessageListResponse } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = '/api/messages'; // Update with your backend URL

  constructor(private http: HttpClient) { }

  getMessages(page: number = 0, size: number = 20): Observable<MessageListResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<MessageListResponse>(this.apiUrl, { params });
  }

  getMessage(id: number): Observable<Message> {
    return this.http.get<Message>(`${this.apiUrl}/${id}`);
  }

  searchByContent(query: string): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/search`, {
      params: { content: query }
    });
  }

  getByCorrelationId(correlationId: string): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/correlation/${correlationId}`);
  }

  getByDateRange(start: Date, end: Date): Observable<Message[]> {
    const startStr = start.toISOString();
    const endStr = end.toISOString();
    return this.http.get<Message[]>(`${this.apiUrl}/date-range`, {
      params: { start: startStr, end: endStr }
    });
  }

  getMessageCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count`);
  }
}