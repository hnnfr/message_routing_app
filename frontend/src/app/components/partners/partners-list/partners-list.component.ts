import { Component, OnDestroy, OnInit } from '@angular/core';
import { PartnerService } from '../../../services/partner.service';
import { Partner } from '../../../models/partner.model';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { TruncatePipe } from '../../../shared/truncate.pipe';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-partners-list',
  standalone: true,
  imports: [
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    MatCardModule,
    MatProgressSpinnerModule, 
    TruncatePipe
  ],
  templateUrl: './partners-list.component.html',
  styleUrls: ['./partners-list.component.scss']
})
export class PartnersListComponent implements OnInit, OnDestroy {
  partners: Partner[] = [];
  displayedColumns: string[] = ['alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];
  isLoading = true;
  private destroy$ = new Subject<void>(); 

  constructor(private partnerService: PartnerService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadPartners();
  }

  loadPartners(): void {
    this.partnerService.getPartners()
      .pipe(takeUntil(this.destroy$)) 
      .subscribe({
        next: (partners) => {
          this.partners = partners;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
        }
    });
  }

  deletePartner(id: number) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { 
        message: 'Are you sure you want to delete this partner?',
        title: 'Confirm Delete'
      }
    });

    dialogRef.afterClosed()
      .pipe(takeUntil(this.destroy$))
      .subscribe(confirmed => {
        if (confirmed) {
          this.isLoading = true;
          this.partnerService.deletePartner(id)
            .pipe(takeUntil(this.destroy$))
            .subscribe({
              next: () => {
                this.loadPartners();
                this.isLoading = false;
              },
              error: (err) => {
                this.isLoading = false;
                console.error(err);
              }
          });
        }
    });
  }

  ngOnDestroy(): void {
    // Unsubscribe from any subscriptions
    this.destroy$.next();
    this.destroy$.complete();
  }

}