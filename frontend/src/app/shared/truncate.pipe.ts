import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate', 
  standalone: true
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit = 25, completeWords = false, ellipsis = '...'): string {
    if (!value) return '';
    
    if (value.length <= limit) {
      return value;
    }

    let truncated = value.substring(0, limit);
    if (completeWords) {
      const lastSpaceIndex = truncated.lastIndexOf(' ');
      truncated = truncated.substring(0, lastSpaceIndex >= 0 ? lastSpaceIndex : limit);
    }

    return `${truncated}${ellipsis}`;
  }
}