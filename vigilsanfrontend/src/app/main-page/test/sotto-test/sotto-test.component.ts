/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { TypeUploadFile } from 'src/app/DTO/PostFileDTO';
import { FileTipiEntity, VociEntity } from 'src/app/DTO/SezioniEntity';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { TypeModule } from '../test.component';

export interface GetFile {

}

@Component({
  selector: 'app-sotto-test',
  templateUrl: './sotto-test.component.html',
  styleUrls: ['./sotto-test.component.css']
})
export class SottoTestComponent implements OnInit {

  @Input() item: any;
  @Input() voceControl: any;
  @Input() voceControlTime: any;

  @ViewChild('f_input') fileInput!: ElementRef;

  styleMatCard: string = "px-4 py-3 mb-2 mat-elevation-z1";
  styleMatFormField: string = "w-100";

  stylePrimaColonna: string = "col-10 col-lg-4";
  stylePrimaColonnaNotCheck: string = "col-12 col-lg-6";
  styleCheckColonna: string = "col-2 col-lg-2 text-center";
  styleSecondaColonna: string = "col-12 col-lg-6";

  enum: typeof TypeModule = TypeModule;

  // Modulo TIME
  public formatTimePicket: number = 24;

  base64String: string | null = null;

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService) { }

  ngOnInit(): void {
  }

  async onFileSelected(event: any, file: FileTipiEntity, voce: VociEntity) {
    const fileUpdated: File = event.target.files[0];
    this.fileInput.nativeElement.value = '';  // Cancello il file selezionato nel native element del componente

    if (file) {
      // Check tipe fileUpdated
      let listType: TypeUploadFile[] = await lastValueFrom(this.client.getTypeUploadFile());
      listType = listType.filter((e) => file.fileContentTypes.includes(e.fileContentTypeId ? e.fileContentTypeId : 0));
      const listTypeMime = listType.map((e) => e.fileContentTypeMime);
      if (!listTypeMime.includes(fileUpdated.type)) {
        this.errorHandlerService.displayErrorMessage('Il tipo del file selezionato non e\' caricabile come allegato. [' + fileUpdated.type + ']');
        return;
      }

      // Check file name
      const regex = /^[a-zA-Z0-9\.\-_]+$/;
      if (!regex.test(fileUpdated.name)) {
        this.errorHandlerService.displayErrorMessage('ERRORE NOME FILE: ' + fileUpdated.name);
        this.errorHandlerService.displayErrorMessage('Puoi inserire: "a-z","A-Z","0-9","-",".","_"');
        return;
      }

      if (voce.fileGruppo?.fileTipi) {
        for (let index = 0; index < voce.fileGruppo?.fileTipi?.length; index++) {
          if (voce.fileGruppo.fileTipi[index].fileTipoId === file.fileTipoId) {
            voce.fileGruppo.fileTipi[index].voceControl.patchValue('File in caricamento ...');
          }
        }
      }

      const base64File = await this.file2Base64(fileUpdated);
      const payload = {
        base64File,
        fileTipoId: file.fileTipoId,
        fileName: fileUpdated.name,
        enteId: this.client.loggedUser.struttura?.asl ? this.client.loggedUser.struttura?.asl[0].enteId : null,
        strutturaId: this.client.loggedUser.struttura?.strutturaId,
        soggettoId: this.client.loggedUser.soggetto?.soggettoId,
        fileContentTypeId: file.fileContentTypes[0]  // TODO aggiungere decodifica e logica per tipo file da caricare
      };

      await lastValueFrom(this.client.postFile(payload))
        .then(postFileObj => {
          // let fileDesc: string | null = '';
          if (voce.fileGruppo?.fileTipi) {
            for (let index = 0; index < voce.fileGruppo?.fileTipi?.length; index++) {
              if (voce.fileGruppo.fileTipi[index].fileTipoId === file.fileTipoId) {
                voce.fileGruppo.fileTipi[index].fileId = postFileObj.fileId;
                voce.fileGruppo.fileTipi[index].fileName = postFileObj.fileName;
                // fileDesc = voce.fileGruppo.fileTipi[index].fileTipoDesc;
                voce.fileGruppo.fileTipi[index].voceControl.patchValue(voce.fileGruppo.fileTipi[index].fileName);
              }
            }
          }
          // if (postFileObj.fileName) {
          //   file.fileNomeCaricato = fileDesc;
          // }
          this.errorHandlerService.displaySuccessMessage('File caricato con successo');
        })
        .catch(
          error => {
            this.errorHandlerService.handleError(error, 'postFile');
          }
        );
    }
  }

  file2Base64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const base64String = e.target.result.split(',')[1]; // Prendi solo la parte di base64
        resolve(base64String);
      };
      reader.onerror = (error) => {
        reject(error);
      };
      reader.readAsDataURL(file);
    });
  }

  async openFileNewTab(fileId: number) {
    if (!fileId) {
      this.errorHandlerService.displayErrorMessage('Errore nel recupero del file');
      return;
    }
    const params = {
      file_id: fileId
    }
    await lastValueFrom(this.client.getFile(params))
      .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              window.open(fileURL, '_blank');
            }
          }
        }
      })
      .catch(
        error => {
          this.errorHandlerService.handleError(error, 'getFile');
        }
      );
  }

  async openFile(fileId: number) {
    if (!fileId) {
      this.errorHandlerService.displayErrorMessage('Errore nel recupero del file');
      return;
    }
    const params = {
      file_id: fileId
    }
    await lastValueFrom(this.client.getFile(params))
      .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              const a = document.createElement('a');
              document.body.appendChild(a);
              a.style.display = 'none';
              a.href = fileURL;
              a.download = fileName;
              a.click();
              window.URL.revokeObjectURL(fileURL);
              document.body.removeChild(a);
            }
          }
        }
      })
      .catch(
        error => {
          this.errorHandlerService.handleError(error, 'getFile');
        }
      );
  }

  // Check if tooltip is disable or not
  checkTooltipCampoCalcolato(item: VociEntity) {
    if (item.regole) {
      if (item.regole.length > 0) {
        if (item.regole[0].moduloVoceRegolaTipo !== 'calc') {
          return true;
        } else {
          return false;
        }
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  // Check if no option is selected in the multi-select list.
  checkSceltaMultipla(item: VociEntity): boolean {
    let flag: boolean = false;
    if (item.lista && item.lista.valori && item.lista.valori.length > 0 && item.voceControl) {
      for (let voceValore of item.voceControl.value) {
        if (voceValore && voceValore > 0) {
          flag = true;
        }
      }
    } else {
      flag = true;
    }
    return flag;
  }
}
