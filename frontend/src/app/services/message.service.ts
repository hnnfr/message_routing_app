import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message, MessageListResponse } from '../models/message.model';
import { environment } from '../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = `${environment.apiUrl}/messages`; 

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
}