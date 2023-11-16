// src/main/java/com/example/cad_login_fb/AlbumPagerAdapter.java
package com.example.cad_login_fb;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class AlbumPagerAdapter extends FragmentPagerAdapter {

    private List<fragment_album_page> albumPages;

    public AlbumPagerAdapter(FragmentManager fm, List<fragment_album_page> albumPages) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.albumPages = albumPages;
    }

    @Override
    public int getCount() {
        return albumPages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return albumPages.get(position);
    }
}
