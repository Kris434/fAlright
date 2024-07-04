# Wstęp

Linia lotnicza “FlightAlright” zamówiła zrealizowanie systemu danych obejmującego informacje dotyczące ich lotów, pracowników, oraz biletów. System powinien zawierać informacje o lotach, w tym terminy lotów, godziny wylotu i przylotu do miejsca docelowego, oraz numer lotu. Dodatkowo w systemie powinny znaleźć się informacje o personelu lotu oraz informacje na temat biletów lotniczych, w których zawarte mają być odpowiednie dane. Do tego należy stworzyć także system raportowania.

# Wymagania niefunkcjonalne

- skalowalne,
- ciemny i jasny motyw,
- intuicyjny
- czytelny
- bezpieczny

# Wymagania funkcjonalne

- Zarządzanie lotami: System powinien umożliwiać rezerwację lotów, zarządzanie nimi (np. dodawanie, deaktywowanie, aktualizowanie lotów), przeglądanie dostępnych lotów.

- Zarządzanie pasażerami: Baza danych powinna przechowywać informacje o pasażerach, umożliwiając rejestrację, aktualizację danych osobowych.

- Zarządzanie rezerwacjami: System powinien umożliwiać tworzenie, modyfikowanie i anulowanie rezerwacji lotów przez pasażerów, personel linii lotniczych oraz wybór miejsc w samolocie przez pasażerów. Godziny wylotu i przylotu do miejsca docelowego, oraz numer lotu.

- Zarządzanie flotą: System powinien przechowywać informacje o samolotach, ich dostępności, harmonogramach lotów.

- Typy użytkowników: 
    - Klient: osoba korzystająca z usług linii lotniczych.
    - Pracownik: osoba zatrudniona w obsłudze lotów. 
    - Administrator: osoba odpowiedzialna za zarządzanie systemem.
    - Niezalogowany użytkownik: osoba, która jeszcze nie zalogowała się do systemu, ale może przeglądać niektóre informacje.
    - Obsługa bagażu: Baza danych powinna śledzić przypisanie bagażu do pasażerów, umożliwiając zarządzanie bagażem, w tym dodawanie, usuwanie i aktualizowanie informacji o bagażu.

- Obsługa personelu: System powinien zawierać informacje o personelu linii lotniczych, ich grafikach pracy, uprawnieniach dostępu do systemu itp.

- Obsługa lotnisk: System powinien zawierać informacje o lotniskach, ich lokalizacji, infrastrukturze, godzinach pracy itp., umożliwiając łatwe planowanie podróży.

- Raportowanie i analiza danych: Baza danych powinna umożliwiać generowanie różnych raportów i analiz na temat wydajności, przychodów, liczby pasażerów itp., wspomagające podejmowanie decyzji biznesowych.

# Analiza metodą MoSCow

## Must:

- Zarządzanie lotami: System musi umożliwiać rezerwację, zarządzanie oraz przeglądanie lotów.

- Zarządzanie pasażerami: Konieczne jest przechowywanie informacji o pasażerach i umożliwienie rejestracji oraz aktualizacji danych.

- Zarządzanie rezerwacjami: Konieczne jest umożliwienie tworzenia, modyfikowania i anulowania rezerwacji przez pasażerów, personel linii lotniczych oraz wybór miejsc w samolocie przez pasażerów. Godziny wylotu i przylotu do miejsca docelowego, oraz numer lotu.

- Zarządzanie flotą: System musi przechowywać informacje o samolotach, ich dostępności, harmonogramach lotów

- Typy użytkowników: 
    - Klient: osoba korzystająca z usług linii lotniczych.
    - Pracownik: osoba zatrudniona w obsłudze lotów. 
    - Administrator: osoba odpowiedzialna za zarządzanie systemem.
    - Niezalogowany użytkownik: osoba, która jeszcze nie zalogowała się do systemu, ale może przeglądać niektóre informacje.

## Should

- Obsługa bagażu: System powinien śledzić przypisanie bagażu do pasażerów i umożliwiać zarządzanie bagażem.

- Obsługa personelu: Wskazane jest zawarcie informacji o personelu linii lotniczych, ich grafikach pracy, uprawnieniach dostępu do systemu itp.

- Raportowanie i analiza danych: Baza danych może umożliwiać generowanie różnych raportów i analiz na temat wydajności, przychodów, liczby pasażerów itp., wspomagające podejmowanie decyzji biznesowych.

## Could

- Obsługa lotnisk: System może zawierać informacje o lotniskach, ich lokalizacji, infrastrukturze, godzinach pracy itp., wspierając planowanie podróży.

## Won't

- Integrowalność z systemami zewnętrznymi: Nie planuje się integracji z systemami innych linii lotniczych ani firm zewnętrznych.
