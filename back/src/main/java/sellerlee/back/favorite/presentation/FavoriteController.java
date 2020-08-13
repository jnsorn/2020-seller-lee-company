/**
 *
 * @author joseph415
 */

package sellerlee.back.favorite.presentation;

import static sellerlee.back.favorite.presentation.FavoriteController.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FAVORITE_URL)
public class FavoriteController {
    public static final String FAVORITE_URL = "/favorites";
}