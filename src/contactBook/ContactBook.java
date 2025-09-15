package contactBook;

import contactBook.Contact;

public class ContactBook {
    static final int DEFAULT_SIZE = 100;

    private int counter;
    private Contact[] contacts;
    private int currentContact;

    public ContactBook() {
        counter = 0;
        contacts = new Contact[DEFAULT_SIZE];
        currentContact = -1;
    }

    //Pre: name != null
    public boolean hasContact(String name) {
        return searchIndex(name) >= 0;
    }

    public boolean hasPhone(int phone) {
        return searchIndexByNumber(phone) >= 0;
    }

    public int getNumberOfContacts() {
        return counter;
    }

    //Pre: name!= null && !hasContact(name)
    public void addContact(String name, int phone, String email) {
        if (counter == contacts.length)
            resize();
        contacts[counter] = new Contact(name, phone, email);
        counter++;
    }

    //Pre: name != null && hasContact(name)
    public void deleteContact(String name) {
        int index = searchIndex(name);
        for(int i=index; i<counter; i++)
            contacts[i] = contacts[i+1];
        counter--;
    }

    //Pre: name != null && hasContact(name)
    public int getPhone(String name) {
        return contacts[searchIndex(name)].getPhone();
    }

    //Pre: name != null && hasContact(name)
    public String getEmail(String name) {
        return contacts[searchIndex(name)].getEmail();
    }

    //Pre: name != null && hasContact(name)
    public void setPhone(String name, int phone) {
        contacts[searchIndex(name)].setPhone(phone);
    }

    //Pre: name != null && hasContact(name)
    public void setEmail(String name, String email) {
        contacts[searchIndex(name)].setEmail(email);
    }

    private int searchIndex(String name) {
        int i = 0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (contacts[i].getName().equals(name))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    // Encontrar a posição do contacto no array de contactos com o respetivo número de telefone
    private int searchIndexByNumber(int number) {
        int i = 0;
        int result = -1;
        while(i < counter && result == -1) {
            if(contacts[i].getPhone() == number)
                result = i;
            else
                i++;
        }
        return result;
    }

    // Devolve o nome do contacto com o número de telefone de input
    public String getContactByNumber(int number) {

        String name = null;
        int pos = searchIndexByNumber(number);

        if (pos != -1)
            name = contacts[pos].getName();
        return name;
    }

    //Para cada contacto verificamos se o seu número de telefone é diferente de todos os contactos à frente
    public boolean existsSamePhoneNumber() {
        int i = 0;
        int j;
        boolean sameNumber = false;

        while(i < counter - 1 && !sameNumber){ //counter - 1 visto que o último número de contacto não tem nenhum à frente
            j = i + 1; //j é o contacto asseguir a i

            while(j < counter && !sameNumber){
                sameNumber = contacts[i].getPhone() == contacts[j].getPhone();
                j++;
            }

            i++;
        }

        return sameNumber;
    }

    private void resize() {
        Contact tmp[] = new Contact[2*contacts.length];
        for (int i=0;i<counter; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
    }

    public void initializeIterator() {
        currentContact = 0;
    }

    public boolean hasNext() {
        return (currentContact >= 0 ) && (currentContact < counter);
    }

    //Pre: hasNext()
    public Contact next() {
        return contacts[currentContact++];
    }
}