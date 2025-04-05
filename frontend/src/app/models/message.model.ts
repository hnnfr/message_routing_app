export interface Message {
  id: number;
  messageId: string;
  content: string;
  correlationId: string;
  receivedTimestamp: Date;
}

export interface MessageListResponse {
  content: Message[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
}