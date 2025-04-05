export enum Direction {
  INBOUND = 'INBOUND',
  OUTBOUND = 'OUTBOUND'
}

export enum FlowType {
  MESSAGE = 'MESSAGE',
  ALERTING = 'ALERTING',
  NOTIFICATION = 'NOTIFICATION'
}

export interface Partner {
  id: number;
  alias: string;
  type: string;
  direction: Direction;
  application: string;
  processedFlowType: FlowType;
  description: string;
}

