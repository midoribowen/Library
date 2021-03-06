import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void authors_instantiateWithFirstNameAndLastName() {
    Author author = new Author("Marx", "Karl");
    author.save();
    assertEquals("Marx", Author.find(author.getId()).getLastName());
    assertEquals("Karl", Author.find(author.getId()).getFirstName());
  }

  @Test
  public void author_deleteWorksProperly_0() {
    Author author = new Author("Marx", "Karl");
    author.save();
    author.delete();
    assertEquals(0, Author.all().size());
  }

  @Test
  public void author_updateWorksProperly() {
    Author author = new Author("Marx", "Karl");
    author.save();
    author.update("Engels", "Freidrich");
    assertEquals(author.getLastName(), "Engels");
    assertEquals(author.getFirstName(), "Freidrich");
  }

  @Test
  public void equals_returnsTrueIfSameFirstNameAndLastName() {
    Author firstAuthor = new Author("Marx", "Karl");
    firstAuthor.save();
    Author secondAuthor = new Author("Marx", "Karl");
    secondAuthor.save();
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void getAllBooks_ListsAllBooksRelatedToAuthor() {
    Author author = new Author("Potter", "Beatrix");
    author.save();
    Book firstBook = new Book("The Tale Peter Rabbit", "Children");
    firstBook.save();
    Book secondBook = new Book("The Tale Squirrel Nutkin",
    "Children");
    secondBook.save();
    Book thirdBook = new Book("Appley Dapply's Nursery Rhymes", "Children");
    thirdBook.save();
    firstBook.addAuthor(author.getId());
    secondBook.addAuthor(author.getId());
    thirdBook.addAuthor(author.getId());
    Book[] books = new Book[] {firstBook, secondBook, thirdBook};
    assertTrue(author.getAllBooks().containsAll(Arrays.asList(books)));
  }

}
